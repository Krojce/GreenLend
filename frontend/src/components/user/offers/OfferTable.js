import React from "react";
import { Table } from "react-bootstrap";
import { OfferTableHead } from "./OfferTableHead";
import { OfferTableRow } from "./OfferTableRow";

export const OfferTable = props => {
  return (
    <Table hover striped>
      <OfferTableHead />
      <tbody>
        {props.items.map((item, i) => (
          <OfferTableRow key={i} item={item} />
        ))}
      </tbody>
    </Table>
  );
};
