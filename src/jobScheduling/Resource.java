package jobScheduling;

public class Resource {
    private char name;           //资源名字
    private int isOccupied;        //资源是否被占用(0:不占用，1：被占用)

    public Resource(char name, int isOccupied) {
        this.name = name;
        this.isOccupied = isOccupied;
    }

    public char getName() {
        return name;
    }

    public void setName(char name) {
        this.name = name;
    }

    public int getIsOccupied() {
        return isOccupied;
    }

    public void setIsOccupied(int isOccupied) {
        this.isOccupied = isOccupied;
    }
}
