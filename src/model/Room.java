package model;

import enums.RoomType;
import error.NotAvaliable;
import io.github.cdimascio.dotenv.Dotenv;
import util.RoomList;

import java.util.stream.Collectors;

import enums.RoomStatus;

public class Room {
    private static int singleRoomCount = 0;
    private static int doubleRoomCount = 0;
    private static int deluxeRoomCount = 0;
    private int roomNumber;
    private RoomType roomType;
    private RoomStatus roomStatus;
    private int user_id;

    public Room() throws NotAvaliable {
        throw new NotAvaliable("RoomType is required");
    }

    public Room(RoomType roomType) {
        switch (roomType) {
            case Single:
                roomNumber = 100 + singleRoomCount++;
                break;

            case Double:
                roomNumber = 200 + doubleRoomCount++;
                break;

            case Deluxe:
                roomNumber = 300 + deluxeRoomCount++;
                break;
        }
        this.roomType = roomType;
        this.roomStatus = RoomStatus.Empty;
        this.user_id = -1;
    }

    public RoomType getRoomType() {
        return this.roomType;
    }

    public RoomStatus getRoomStatus() {
        return this.roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public double getPrice() {
        switch (this.roomType) {
            case Single:
                return Double.parseDouble(Dotenv.load().get("SINGLE_ROOM_PRICE", "30"));
            case Double:
                return Double.parseDouble(Dotenv.load().get("DOUBLE_ROOM_PRICE", "50"));
            case Deluxe:
                return Double.parseDouble(Dotenv.load().get("DELUXE_ROOM_PRICE", "100"));
            default:
                return 0.0;
        }
    }

    public int getID() {
        return this.user_id;
    }

    public void setID(int id) {
        this.user_id = id;
    }

    public static RoomList all() {
        RoomList roomList = new RoomList();
        for (int i = 0; i < Integer.parseInt(Dotenv.load().get("SINGLE_ROOMS", "0")); i++) {
            roomList.add(new Room(RoomType.Single));
        }
        for (int i = 0; i < Integer.parseInt(Dotenv.load().get("DOUBLE_ROOMS", "0")); i++) {
            roomList.add(new Room(RoomType.Double));
        }
        for (int i = 0; i < Integer.parseInt(Dotenv.load().get("DELUXE_ROOMS", "0")); i++) {
            roomList.add(new Room(RoomType.Deluxe));
        }
        return roomList;
    }

    public static RoomList getRoomsByStatus(RoomStatus roomStatus, RoomList rooms) {
        return rooms.stream().filter((room) -> roomStatus.equals(room.roomStatus))
                .collect(Collectors.toCollection(RoomList::new));
    }

    public static RoomList getRoomsByRoomType(RoomType roomType, RoomList rooms) {
        return rooms.stream().filter((room) -> roomType.equals(room.roomType))
                .collect(Collectors.toCollection(RoomList::new));
    }

    public static RoomList getRoomsByBoth(RoomStatus roomStatus, RoomType roomType, RoomList rooms) {
        return rooms.stream().filter((room) -> roomStatus.equals(room.roomStatus) && roomType.equals(room.roomType))
                .collect(Collectors.toCollection(RoomList::new));
    }

    @Override
    public String toString() {
        return String.format("%3d %10s %10s %15s", this.roomNumber, this.roomType, this.roomStatus,
                this.user_id == -1 ? "Avaliable" : this.user_id) + "\n";
    }
}
