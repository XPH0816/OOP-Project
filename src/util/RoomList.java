package util;

import model.Room;

import java.util.ArrayList;
import interfaces.Searchable;

public class RoomList extends ArrayList<Room> implements Searchable<RoomList>{
    @Override 
    public RoomList searchByUserID(int id) {
        RoomList result = new RoomList();
        for (Room room : this) {
            if (room.getID() == id) {
                result.add(room);
            }
        }
        return result;
    }

    public double getTotalPrice() {
        double result = 0.0;
        for (Room room : this) {
            result += room.getPrice();
        }
        return result;
    }

    @Override
    public String toString() {
        if(this.size() == 0) return String.format("No room");
        String result = "";
        result += String.format("====================================================") + "\n";
        result += String.format("  No RoomID %10s %10s %12s", "Type", "Status", "User") + "\n";
        result += String.format("====================================================") + "\n";
        for (Room room : this) {
            result += String.format(" %3d %s", indexOf(room) + 1, room);
        }
        return result;
    }
}
