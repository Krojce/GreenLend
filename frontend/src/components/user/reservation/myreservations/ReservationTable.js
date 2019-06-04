import React from "react";
import { Table } from "react-bootstrap";
import { ReservationTableRow } from "./ReservationTableRow";
import { ReservationTableHead } from "./ReservationTableHead";

export const ReservationTable = props => {
  return (
    <Table hover striped>
      <ReservationTableHead/>
      <tbody>
      {props.items.map((item, i) => (
        <ReservationTableRow key={i} item={item} redirect={props.redirect}/>
      ))}
      </tbody>
    </Table>
  );
};