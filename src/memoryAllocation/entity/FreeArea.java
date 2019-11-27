package memoryAllocation.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * 分区
 */
public class FreeArea {
    private final IntegerProperty size = new SimpleIntegerProperty();        //分区大小
    private final IntegerProperty startArea = new SimpleIntegerProperty();   //分区开始地址
    private final IntegerProperty state = new SimpleIntegerProperty();       //分区是否被占用，0：不被占用，1：被占用
    private String name;     //如果有作业占用，进程名称

    //xxxProperty方法名，是fx的规范，只要属性名加上这个方法，fx就能自动监听该属性的变化
    public IntegerProperty sizeProperty(){
        return size;
    }

    public int getSize() {
        return size.get();
    }

    public void setSize(int size) {
        this.size.set(size);
    }

    public IntegerProperty startAreaProperty(){
        return startArea;
    }

    public int getStartArea() {
        return startArea.get();
    }

    public void setStartArea(int startArea) {
        this.startArea.set(startArea);
    }

    public IntegerProperty stateProperty(){
        return state;
    }

    public int getState() {
        return state.get();
    }

    public void setState(int state) {
        this.state.set(state);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FreeArea(int size, int startArea, int state) {
        this.size.set(size);
        this.startArea.set(startArea);
        this.state.set(state);
    }

    public FreeArea(String name,int size, int startArea, int state) {
        this.name = name;
        this.size.set(size);
        this.startArea.set(startArea);
        this.state.set(state);
    }
}
