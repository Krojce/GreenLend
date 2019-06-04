import { Card } from "react-bootstrap";
import React from "react";

export const Item = data => {
  const { itemData, onClick } = data || {};
  const { thumbnail } = itemData || {};
  return (
    <Card
      className="offer-item bg-dark text-white"
      onClick={() => onClick(itemData)}
    >
      <Card.Img src={thumbnail} alt={itemData.name} />
      <Card.ImgOverlay>
        <Card.Title>{itemData.name}</Card.Title>
        <Card.Text>{itemData.price + "Kč/den"}</Card.Text>
      </Card.ImgOverlay>
    </Card>
  );
};
