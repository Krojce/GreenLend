import React from "react";
import { Table } from "react-bootstrap";
import { BookingTableHead } from "./BookingTableHead";
import { BookingTableRow } from "./BookingTableRow";

export const BookingTable = props => {
  return (
    <Table hover striped>
      <BookingTableHead />
      <tbody>
        {props.items.map((item, i) => (
          <BookingTableRow key={i} item={item} redirect={props.redirect} />
        ))}
      </tbody>
    </Table>
  );
};
