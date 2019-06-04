import React from "react";
import { OverlayTrigger, Popover } from "react-bootstrap";
import { dateFormat } from "../../utils/DateFormat";

export const OfferTableRow = props => {
  const { item } = props;
  const categories = item.categories.map(category => category.name);
  console.log(item);
  return (
    <OverlayTrigger
      placement="top-start"
      trigger="hover"
      delay={{ show: 250 }}
      overlay={
        <Popover className="PopoverThumbnail">
          <img src={item.thumbnail} alt="thumbnail" />
        </Popover>
      }
    >
      <tr>
        <td>{item.name}</td>
        <td>{categories.join(", ") || "-"}</td>
        <td>{item.nextLend ? dateFormat(item.nextLend) : "-"}</td>
        <td>{`${item.price}Kč`}</td>
        <td>{`${item.revenue}Kč`}</td>
      </tr>
    </OverlayTrigger>
  );
};

/*
  <th>Položka</th>
  <th></th>
  <th>Příští vypůjčení</th>
  <th>Cena</th>
  <th>Obrat</th>
*/
