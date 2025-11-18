package com.oyo.Factory;

import com.oyo.Enums.RoomStyle;
import com.oyo.Enums.RoomType;
import com.oyo.Models.Room;

public class RoomFactory {
 public static Room createRoom(String roomNumber, String type, String style, double price) {
        RoomType roomType = RoomType.valueOf(type.toUpperCase());
        RoomStyle roomStyle = RoomStyle.valueOf(style.toUpperCase());
        return new Room(roomNumber, roomType, roomStyle, price);
    }

    public static class createRoom {

        public createRoom() {
        }
    }
}
