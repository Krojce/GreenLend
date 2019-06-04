import React, { Component } from "react";
import { Button, FormControl, InputGroup } from "react-bootstrap";
import { ThreadItem } from "./ThreadItem";
import { Message } from "./Message";
import { NoMessagesYet } from "./NoMessagesYet";
import { getChats, sendMessage } from "./../../../apis/greenLendApi";
import { connect } from "react-redux";
import { getChatFailure, getChatInit, getChatSuccess } from "../../../actions/chat/chatApiActions";
import { setCurrentThread, setMessage } from "../../../actions/chat/chatDataActions";
import { Loading } from "../../utils/Loading";

class Chat extends Component {
  componentWillMount() {
    this.updateMessages();
    this.interval = setInterval(() => this.updateMessages(), 5000);
  }

  updateMessages = () => {
    const {
      getChatInit,
      getChatSuccess,
      getChatFailure,
      setCurrentThread
    } = this.props;
    const { currentThread } = this.props.chatData;

    getChatInit();
    getChats()
      .then(response => {
        if (currentThread == null) {
          console.log("Changing thread");
          setCurrentThread(
            response.data.length > 0 ? response.data[0].id : null
          );
        }
        response.data.forEach(t =>
          t.messages.forEach(m => {
            m.fromMe = (m.fromOwner && t.lend) || (!m.fromOwner && !t.lend);
            m.sentTime = Date.parse(m.sentTime);
          })
        );
        getChatSuccess(response.data);
      })
      .catch(error => getChatFailure(error));
  };

  componentWillUnmount() {
    clearInterval(this.interval);
  }

  renderThreadItems() {
    const { chat } = this.props.chatInfo;
    const { currentThread } = this.props.chatData;

    return (
      <>
        {chat.map(thread => (
          <ThreadItem
            thread={thread}
            key={thread.id}
            func={this.changeThread}
            active={currentThread === thread.id}
          />
        ))}
      </>
    );
  }

  renderMessages() {
    if (this.currentThread() === undefined) return null;
    return (
      <>
        {this.currentThread()
          .messages.sort((a, b) => b.sentTime - a.sentTime)
          .map((m, i) => (
            <Message message={m} key={i} />
          ))}
      </>
    );
  }

  currentThread() {
    const { currentThread } = this.props.chatData;
    const { chat } = this.props.chatInfo;
    return chat.find(thread => thread.id === currentThread);
  }

  changeThread = thread => {
    const { setCurrentThread } = this.props;
    setCurrentThread(thread);
  };

  currentThreadName() {
    if (this.currentThread() === undefined) return null;
    let t = this.currentThread();
    return `${t.opponent} ${
      t.lend ? "si od vás půjčuje předmět: " : "vám půjčuje předmět: "
    } ${t.offerName}`;
  }

  sendMessageWithEnter = event => {
    if (event.key === "Enter") this.sendCurrentMessage();
  };

  sendCurrentMessage = () => {
    const { setMessage } = this.props;
    const { message, currentThread } = this.props.chatData;

    sendMessage(currentThread, message)
      .then(() => {
        setMessage("");
        this.updateMessages();
      })
      .catch(console.error);
  };

  sendPhoneNumber = () => {
    const { phone } = this.props.userInfo.user;
    const { currentThread } = this.props.chatData;

    sendMessage(currentThread, `Moje telefonní číslo: ${phone}`)
      .then(() => {
        this.updateMessages();
      })
      .catch(console.error);
  };

  handleMessageChange = event => {
    const { setMessage } = this.props;
    setMessage(event.target.value);
  };

  render() {
    const { chatData, chatInfo } = this.props;
    const { loading, chat } = chatInfo;
    const { message } = chatData;
    const { phone } = this.props.userInfo.user;

    if (chat.length === 0 && !loading) return <NoMessagesYet/>;

    return (
      <div className="messagesGrid">
        <div className="threads-heading">
          {loading ? <Loading/> : "Zprávy"}
        </div>
        <div className="threads">{this.renderThreadItems()}</div>
        <div className="messages-heading">{this.currentThreadName()}</div>
        <div className="messages">{this.renderMessages()}</div>
        <div className="compose">
          <InputGroup>
            <FormControl
              placeholder="Napište zprávu"
              aria-label="Zpráva"
              onChange={this.handleMessageChange}
              value={message}
              onKeyPress={this.sendMessageWithEnter}
            />
            <InputGroup.Append>
              <Button
                variant="outline-success"
                onClick={this.sendCurrentMessage}
              >
                Odeslat
              </Button>
              {phone ? (
                <Button
                  variant="outline-success"
                  onClick={this.sendPhoneNumber}
                  title="Odeslat telefonní číslo"
                >
                  <i className="fas fa-phone" />
                </Button>
              ) : null}
            </InputGroup.Append>
          </InputGroup>
        </div>
      </div>
    );
  }
}

const mapStateToProps = state => {
  return {
    chatInfo: state.chatInfo,
    chatData: state.chatData,
    userInfo: state.userInfo
  };
};

export default connect(
  mapStateToProps,
  {
    getChatInit,
    getChatSuccess,
    getChatFailure,
    setCurrentThread,
    setMessage
  }
)(Chat);
