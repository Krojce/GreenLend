import React from "react";
import { Badge } from "react-bootstrap";

export const ThreadItem = props => (
  <div
    className={`thread${props.active ? " active" : ""}`}
    onClick={e => {
      e.preventDefault();
      props.func(props.thread.id);
    }}
  >
    <div>
      {props.thread.offerName}{" "}
      {props.thread.lend ? <Badge variant="success">Vaše nabídka</Badge> : null}
    </div>
    <div>{props.thread.opponent}</div>
    <div>
      {props.thread.messages.length > 0
        ? (props.thread.messages[0].fromMe ? "Vy: " : "") +
          props.thread.messages[0].content
        : "Napiště první zprávu"}
    </div>
  </div>
);
