import React from "react";

export const Message = props => (
  <div
    className={`message ${props.message.fromMe ? "out" : "in"}`}
    time={props.message.sentTime}
  >
    {props.message.content}
  </div>
);
