package cn.panda.game.pandastore.bean;

import android.text.TextUtils;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GameListBean
{
    private String resultDesc;
    private int resultCode;
    private String status;
    private Data data;

    public String getResultDesc () {
        return resultDesc;
    }

    public void setResultDesc (String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public int getResultCode () {
        return resultCode;
    }

    public void setResultCode (int resultCode) {
        this.resultCode = resultCode;
    }

    public String getStatus () {
        return status;
    }

    public void setStatus (String status) {
        this.status = status;
    }

    public Data getData () {
        return data;
    }

    public void setData (Data data) {
        this.data = data;
    }

    public static class Data
    {
        private int total_pages;
        private int game_count;
        private List<Page> page_data;
        private int page_size;
        private int current_page;


        public int getTotal_pages () {
            return total_pages;
        }

        public void setTotal_pages (int total_pages) {
            this.total_pages = total_pages;
        }

        public int getGame_count () {
            return game_count;
        }

        public void setGame_count (int game_count) {
            this.game_count = game_count;
        }

        public List<Page> getPage_data() {
            return page_data;
        }

        public void setPage_data(List<Page> page_data) {
            this.page_data = page_data;
        }
        public void addPage (Page page)
        {
            if (this.page_data == null)
            {
                this.page_data  = new ArrayList<>();
            }
            this.page_data.add(page);
        }

        public int getPage_size () {
            return page_size;
        }

        public void setPage_size (int page_size) {
            this.page_size = page_size;
        }

        public int getCurrent_page () {
            return current_page;
        }

        public void setCurrent_page (int current_page) {
            this.current_page = current_page;
        }
    }

    public static class Page
    {
        private String showType;
        private String title;
        private String more;
        private String background;

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public String getMore() {
            return more;
        }

        public void setMore(String more) {
            this.more = more;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        private List<Game> gameslist;
        public String getShowType()
        {
            return showType;
        }

        public void setShowType(String showType) {
            this.showType = showType;
        }

        public List<Game> getGameslist() {
            return gameslist;
        }

        public void setGameslist(List<Game> gameslist) {
            this.gameslist = gameslist;
        }
        public void addGame (Game game)
        {
            if (this.gameslist == null)
            {
                this.gameslist  = new ArrayList<>();
            }
            this.gameslist.add(game);
        }


    }
    public static class Game
    {
        private String sub_title;
        private String discount_start;
        private String banner;
        private int download_count;
        private String first_discount;
        private String name;
        private String category;
        private String id;
        private String second_discount;
        private String size;
        private String icon;
        private String tag;
        private String discount_end;


        private String jsonStr;

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public String getDiscount_start() {
            return discount_start;
        }

        public void setDiscount_start(String discount_start) {
            this.discount_start = discount_start;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public int getDownload_count() {
            return download_count;
        }

        public void setDownload_count(int download_count) {
            this.download_count = download_count;
        }

        public String getFirst_discount() {
            return first_discount;
        }

        public void setFirst_discount(String first_discount) {
            this.first_discount = first_discount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSecond_discount() {
            return second_discount;
        }

        public void setSecond_discount(String second_discount) {
            this.second_discount = second_discount;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getDiscount_end() {
            return discount_end;
        }

        public void setDiscount_end(String discount_end) {
            this.discount_end = discount_end;
        }

        public String getJsonStr() {
            return jsonStr;
        }

        public void setJsonStr(String jsonStr) {
            this.jsonStr = jsonStr;
        }
    }
}
