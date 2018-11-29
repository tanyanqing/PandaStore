package cn.panda.game.pandastore.bean;

/**
 * 游戏详情页面
 */
public class GameDetailBean
{
    private String resultDesc;
    private String status;
    private int resultCode;
    private Data data;

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data
    {
        private String name;
        private String discount_start;
        private String discount_end;
        private String download_count;
        private String show_pic5;
        private String show_pic3;
        private String show_pic4;
        private String tag;
        private String second_discount;
        private String first_discount;
        private String sub_title;
        private String banner;
        private String version;
        private String icon;
        private String show_pic2;
        private String show_pic1;
        private String category;
        private String related_game;
        private String size;
        private String description;
        private String id;
        private String opt_time;
        private String game_no;

        public String getGame_no () {
            return game_no;
        }

        public void setGame_no (String game_no) {
            this.game_no = game_no;
        }

        public String getOpt_time() {
            return opt_time;
        }

        public void setOpt_time(String opt_time) {
            this.opt_time = opt_time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDiscount_start() {
            return discount_start;
        }

        public void setDiscount_start(String discount_start) {
            this.discount_start = discount_start;
        }

        public String getDiscount_end() {
            return discount_end;
        }

        public void setDiscount_end(String discount_end) {
            this.discount_end = discount_end;
        }

        public String getDownload_count() {
            return download_count;
        }

        public void setDownload_count(String download_count) {
            this.download_count = download_count;
        }

        public String getShow_pic5() {
            return show_pic5;
        }

        public void setShow_pic5(String show_pic5) {
            this.show_pic5 = show_pic5;
        }

        public String getShow_pic3() {
            return show_pic3;
        }

        public void setShow_pic3(String show_pic3) {
            this.show_pic3 = show_pic3;
        }

        public String getShow_pic4() {
            return show_pic4;
        }

        public void setShow_pic4(String show_pic4) {
            this.show_pic4 = show_pic4;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getSecond_discount() {
            return second_discount;
        }

        public void setSecond_discount(String second_discount) {
            this.second_discount = second_discount;
        }

        public String getFirst_discount() {
            return first_discount;
        }

        public void setFirst_discount(String first_discount) {
            this.first_discount = first_discount;
        }

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getShow_pic2() {
            return show_pic2;
        }

        public void setShow_pic2(String show_pic2) {
            this.show_pic2 = show_pic2;
        }

        public String getShow_pic1() {
            return show_pic1;
        }

        public void setShow_pic1(String show_pic1) {
            this.show_pic1 = show_pic1;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getRelated_game() {
            return related_game;
        }

        public void setRelated_game(String related_game) {
            this.related_game = related_game;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
