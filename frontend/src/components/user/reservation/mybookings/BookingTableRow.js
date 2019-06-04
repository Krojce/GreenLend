import React from "react";
import { dateFormat } from "../../../utils/DateFormat";

export const BookingTableRow = props => {
  return (
    <tr>
      <td>{props.item.name}</td>
      <td>{dateFormat(props.item.from)}</td>
      <td>{dateFormat(props.item.to)}</td>
      {props.item.isLend !== undefined ? (
        <td className={props.item.isLend ? "text-success" : "text-danger"}>{`${
          props.item.isLend ? "+" : "-"
        }${props.item.price}Kč`}</td>
      ) : (
        <td>{`${props.item.price}Kč`}</td>
      )}
      <td>
        <i
          className="far fa-comment-dots"
          onClick={() => props.redirect(props.item.chatId)}
        />
      </td>
    </tr>
  );
};
