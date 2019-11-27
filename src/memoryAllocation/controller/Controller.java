package memoryAllocation.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import memoryAllocation.entity.FreeArea;

import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    public TextField memorySizeField;
    @FXML
    public TextField processNameField;
    @FXML
    public TextField askSizeField;
    @FXML
    public TextField relNameField;
    @FXML
    public Group myGroup;
    @FXML
    public TableView myTable;
    @FXML
    public RadioButton firstFit;
    @FXML
    public RadioButton bestFit;
    @FXML
    public RadioButton worstFit;

    //图形化界面中内存画板的宽和高
    private static int paneWidth = 160;
    private static int paneHeight = 560;
    //分区链表
    private List<FreeArea> freeAreaList = new LinkedList<>();
    //内存大小
    private int memorySize = 0;
    private TableColumn startAreaColumn;
    private TableColumn sizeColumn;
    private TableColumn stateColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startAreaColumn = myTable.getVisibleLeafColumn(0);
        sizeColumn = myTable.getVisibleLeafColumn(1);
        stateColumn = myTable.getVisibleLeafColumn(2);
        startAreaColumn.setCellValueFactory(new PropertyValueFactory<>("startArea"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
    }

    //初始化内存(或重置)
    public void initializeMemory(ActionEvent actionEvent){
        memorySize = Integer.parseInt(memorySizeField.getText());
        FreeArea freeArea = new FreeArea(memorySize,0,0);
        freeAreaList.clear();
        freeAreaList.add(freeArea);
        paint();
        list();
    }

    //内存分配图形化
    private void paint(){
        int y = paneHeight;
        for(FreeArea freeArea:freeAreaList){
            int height = paneHeight*freeArea.getSize() /memorySize;
            y = y - height;
            Rectangle rectangle = new Rectangle(0,y,paneWidth,height);
            if(freeArea.getState() == 0)
                rectangle.setFill(Color.GOLD);
            else rectangle.setFill(Color.GREEN);
            rectangle.setStroke(Color.BEIGE);
            Label label = new Label(freeArea.getName());
            label.setLayoutX(paneWidth/2);
            label.setLayoutY(y + height/2);
            myGroup.getChildren().addAll(rectangle,label);
        }
    }

    //列出分区表
    private void list(){
        ObservableList<FreeArea> data = FXCollections.observableArrayList();
        for(FreeArea freeArea:freeAreaList){
            data.add(freeArea);
        }
        myTable.setItems(data);
    }

    //申请内存
    public void applyMemory(ActionEvent actionEvent){
        String name = processNameField.getText();
        int size = Integer.parseInt(askSizeField.getText());
        if(firstFit.isSelected()){
            firstFit(name,size);
        }else if(bestFit.isSelected()){
            bestFit(name,size);
        }else {
            worstFit(name,size);
        }
    }

    //首次适应算法
    private void firstFit(String name,int size){
        boolean isAllocated = false;
        for(int i = 0;i < freeAreaList.size();i ++){
            FreeArea freeArea = freeAreaList.get(i);
            if(freeArea.getState() == 0 && freeArea.getSize() >= size){
                FreeArea freeArea1 = new FreeArea(name,size,freeArea.getStartArea(),1);
                freeAreaList.remove(i);
                freeAreaList.add(i,freeArea1);
                if(freeArea.getSize() > size) {
                    FreeArea freeArea2 = new FreeArea(freeArea.getSize() - size,freeArea.getStartArea() + size,0 );
                    freeAreaList.add(i + 1,freeArea2);
                }
                isAllocated = true;
            }
            if(isAllocated)
                break;
        }
        if(!isAllocated)
            showMessage("内存不够！");
        else {
            paint();
            list();
        }
    }

    //最佳适应算法
    private void bestFit(String name,int size){
        int index = -1;
        int curSize = Integer.MAX_VALUE;
        for(int i = 0;i < freeAreaList.size();i ++){
            FreeArea freeArea = freeAreaList.get(i);
            if(freeArea.getSize() > size && freeArea.getSize() < curSize &&freeArea.getState() == 0){
                index = i;
                curSize = freeArea.getSize();
            }
        }
        if(index == -1)
            showMessage("内存不够！");
        else {
            FreeArea freeArea = freeAreaList.get(index);
            FreeArea freeArea1 = new FreeArea(name,size,freeArea.getStartArea(),1);
            freeAreaList.remove(index);
            freeAreaList.add(index,freeArea1);
            if(freeArea.getSize() > size){
                FreeArea freeArea2 = new FreeArea(freeArea.getSize() - size,freeArea.getStartArea() + size,0 );
                freeAreaList.add(index + 1,freeArea2);
            }
            paint();
            list();
        }
    }

    //最差适应算法
    private void worstFit(String name,int size){
        int index = -1;
        int curSize = Integer.MIN_VALUE;
        for(int i = 0;i < freeAreaList.size();i ++){
            FreeArea freeArea = freeAreaList.get(i);
            if(freeArea.getSize() > size && freeArea.getSize() > curSize &&freeArea.getState() == 0){
                index = i;
                curSize = freeArea.getSize();
            }
        }
        if(index == -1)
            showMessage("内存不够！");
        else {
            FreeArea freeArea = freeAreaList.get(index);
            FreeArea freeArea1 = new FreeArea(name,size,freeArea.getStartArea(),1);
            freeAreaList.remove(index);
            freeAreaList.add(index,freeArea1);
            if(freeArea.getSize() > size){
                FreeArea freeArea2 = new FreeArea(freeArea.getSize() - size,freeArea.getStartArea() + size,0 );
                freeAreaList.add(index + 1,freeArea2);
            }
            paint();
            list();
        }
    }

    //释放内存
    public void releaseMemory(ActionEvent actionEvent){
        String name = relNameField.getText();
        for(int i = 0;i < freeAreaList.size();i ++){
            FreeArea freeArea = freeAreaList.get(i);
            if(freeArea.getName() != null && freeArea.getName().equals(name)){
                releaseMemoryHelper(i);
            }
        }
        paint();
        list();
    }
    private void releaseMemoryHelper(int index){
        FreeArea freeArea = freeAreaList.get(index);
        if(index >=1 && freeAreaList.get(index-1).getState() == 0 && index <freeAreaList.size() - 1 && freeAreaList.get(index + 1).getState() == 0){
            int start = freeAreaList.get(index - 1).getStartArea();
            int size = freeAreaList.get(index - 1).getSize() + freeArea.getSize() + freeAreaList.get(index + 1).getSize();
            FreeArea freeArea1 = new FreeArea(size,start,0);
            freeAreaList.remove(index - 1);
            freeAreaList.remove(index );
            freeAreaList.remove(index + 1);
            freeAreaList.add(index - 1,freeArea1);
        }else if(index >=1 && freeAreaList.get(index-1).getState() == 0 && ((index <freeAreaList.size() - 1 && freeAreaList.get(index + 1).getState() == 1) || index == freeAreaList.size())){
            int start = freeAreaList.get(index - 1).getStartArea();
            int size = freeAreaList.get(index - 1).getSize() + freeArea.getSize();
            FreeArea freeArea1 = new FreeArea(size,start,0);
            freeAreaList.remove(index);
            freeAreaList.remove(index - 1);
            freeAreaList.add(index - 1,freeArea1);
        }else if(((index >=1 && freeAreaList.get(index-1).getState() ==1) || index == 0) && index <freeAreaList.size() - 1 && freeAreaList.get(index + 1).getState() == 0){
            int size = freeAreaList.get(index + 1).getSize() + freeArea.getSize();
            freeAreaList.remove(index + 1);
            freeArea.setState(0);
            freeArea.setSize(size);
            freeArea.setName(null);
        }else{
            freeArea.setState(0);
            freeArea.setName(null);
        }
    }
    private void showMessage(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("信息");
        alert.headerTextProperty().set(msg);
        alert.showAndWait();
    }
}
