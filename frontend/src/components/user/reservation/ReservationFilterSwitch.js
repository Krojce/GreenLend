import { Button, ButtonGroup } from "react-bootstrap";
import React from "react";
import { ReservationFilter } from "./ReservationFilter";

export const ReservationFilterSwitch = data => {
  const { changeFilter, activeFilter } = data;
  return (
    <ButtonGroup className="shadow-none mb-3">
      <Button
        variant="light"
        className="shadow-none"
        active={activeFilter === ReservationFilter.borrows}
        onClick={() => changeFilter(ReservationFilter.borrows)}
      >
        Moje rezervace
      </Button>
      <Button
        variant="light"
        className="shadow-none"
        active={activeFilter === ReservationFilter.lends}
        onClick={() => changeFilter(ReservationFilter.lends)}
      >
        Rezervace mých nabídek
      </Button>
    </ButtonGroup>
  );
};
