import React, { Component } from "react";
import { Form } from "react-bootstrap";
import { connect } from "react-redux";
import DatePicker from "react-datepicker/es";

import "react-datepicker/dist/react-datepicker.css";
import { setEndDate, setStartDate } from "../../actions/item/itemDataActions";

class CustomDatePicker extends Component {
  handleChangeStart = from => {
    const { setStartDate, setEndDate } = this.props;
    const { to } = this.props.dateInputs;
    if (from > new Date(to)) {
      setEndDate(new Date(from).toISOString());
    }
    setStartDate(from.toISOString());
  };

  handleChangeEnd = to => {
    const { setStartDate, setEndDate } = this.props;
    const { from } = this.props.dateInputs;
    if (to < new Date(from)) {
      setStartDate(new Date(to).toISOString());
    }
    setEndDate(to.toISOString());
  };

  componentDidMount() {
    this.styleMofoDatePicker();
  }

  styleMofoDatePicker() {
    let inputFrom = document.querySelector("#dateFrom");
    let inputTo = document.querySelector("#dateTo");
    inputFrom.parentNode.parentNode.prepend(inputFrom);
    inputFrom.parentNode.removeChild(inputFrom.nextSibling);
    inputFrom.parentElement.classList.add("col");
    inputTo.parentNode.parentNode.prepend(inputTo);
    inputTo.parentNode.removeChild(inputTo.nextSibling);
    inputTo.parentElement.classList.add("col");
  }

  render() {
    console.log(this.props);

    const { from, to } = this.props.dateInputs;
    const { excluded = [] } = this.props;

    let array = [];
    excluded.forEach(booking => {
      array.push(...getDateArray(booking.from, booking.to));
    });

    return (
      <>
        <Form.Group className="mt-3">
          <Form.Label>Zvolte termín (od - do)</Form.Label>
          <Form.Row>
            <DatePicker
              selected={new Date(from)}
              selectsStart
              startDate={new Date(from)}
              endDate={new Date(to)}
              onChange={this.handleChangeStart}
              minDate={new Date()}
              excludeDates={array}
              className="form-control"
              id="dateFrom"
            />
            <DatePicker
              selected={new Date(to)}
              selectsEnd
              startDate={new Date(from)}
              endDate={new Date(to)}
              onChange={this.handleChangeEnd}
              minDate={new Date()}
              excludeDates={array}
              className="form-control"
              id="dateTo"
            />
          </Form.Row>
        </Form.Group>
      </>
    );
  }
}

const mapStateToProps = state => {
  return {
    dateInputs: state.dateInputs
  };
};

export default connect(
  mapStateToProps,
  {
    setStartDate,
    setEndDate
  }
)(CustomDatePicker);

let getDateArray = function(start, end) {
  let formattedStart = formatDate(start);
  let formattedEnd = formatDate(end);

  let array = [],
    startingDate = new Date(
      formattedStart[0],
      formattedStart[1] - 1,
      formattedStart[2]
    ),
    endingDate = new Date(
      formattedEnd[0],
      formattedEnd[1] - 1,
      formattedEnd[2]
    );

  while (startingDate <= endingDate) {
    array.push(new Date(startingDate.getTime()));
    startingDate.setDate(startingDate.getDate() + 1);
  }

  return array;
};

let formatDate = function(date) {
  return date.split("-");
};
