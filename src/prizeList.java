public class prizeList {
    public prizeList(){}

    public prizeList(String prize,String type){
        this.prize = prize;
        this.type = type;
    }


    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String prize;
    private String type;
}
