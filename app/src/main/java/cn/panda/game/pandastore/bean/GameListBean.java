package cn.panda.game.pandastore.bean;

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
        private List<Game> games;
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

        public List<Game> getGames ()
        {
            if (this.games == null)
            {
                this.games  = new ArrayList<> ();
            }
            return games;
        }

        public void setGames (List<Game> games) {
            this.games = games;
        }
        public void addGame (Game game)
        {
            if (this.games == null)
            {
                this.games  = new ArrayList<> ();
            }
            this.games.add (game);
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
    public static class Game
    {
        private String sub_title;
        private String show_pic2;
        private String first_discount;
        private int download_count;
        private String name;
        private String download_url;
        private String icon;
        private String tag;
        private String category;
        private String discount_end;
        private String second_discount;
        private String size;
        private String show_pic3;
        private String discount_start;
        private String related_game;
        private String version;
        private String banner;
        private String description;
        private String show_pic5;
        private String show_pic1;
        private String show_pic4;

        public String getSub_title () {
            return sub_title;
        }

        public void setSub_title (String sub_title) {
            this.sub_title = sub_title;
        }

        public String getShow_pic2 () {
            return show_pic2;
        }

        public void setShow_pic2 (String show_pic2) {
            this.show_pic2 = show_pic2;
        }

        public String getFirst_discount () {
            return first_discount;
        }

        public void setFirst_discount (String first_discount) {
            this.first_discount = first_discount;
        }

        public int getDownload_count () {
            return download_count;
        }

        public void setDownload_count (int download_count) {
            this.download_count = download_count;
        }

        public String getName () {
            return name;
        }

        public void setName (String name) {
            this.name = name;
        }

        public String getDownload_url () {
            return download_url;
        }

        public void setDownload_url (String download_url) {
            this.download_url = download_url;
        }

        public String getIcon () {
            return icon;
        }

        public void setIcon (String icon) {
            this.icon = icon;
        }

        public String getTag () {
            return tag;
        }

        public void setTag (String tag) {
            this.tag = tag;
        }

        public String getCategory () {
            return category;
        }

        public void setCategory (String category) {
            this.category = category;
        }

        public String getDiscount_end () {
            return discount_end;
        }

        public void setDiscount_end (String discount_end) {
            this.discount_end = discount_end;
        }

        public String getSecond_discount () {
            return second_discount;
        }

        public void setSecond_discount (String second_discount) {
            this.second_discount = second_discount;
        }

        public String getSize () {
            return size;
        }

        public void setSize (String size) {
            this.size = size;
        }

        public String getShow_pic3 () {
            return show_pic3;
        }

        public void setShow_pic3 (String show_pic3) {
            this.show_pic3 = show_pic3;
        }

        public String getDiscount_start () {
            return discount_start;
        }

        public void setDiscount_start (String discount_start) {
            this.discount_start = discount_start;
        }

        public String getRelated_game () {
            return related_game;
        }

        public void setRelated_game (String related_game) {
            this.related_game = related_game;
        }

        public String getVersion () {
            return version;
        }

        public void setVersion (String version) {
            this.version = version;
        }

        public String getBanner () {
            return banner;
        }

        public void setBanner (String banner) {
            this.banner = banner;
        }

        public String getDescription () {
            return description;
        }

        public void setDescription (String description) {
            this.description = description;
        }

        public String getShow_pic5 () {
            return show_pic5;
        }

        public void setShow_pic5 (String show_pic5) {
            this.show_pic5 = show_pic5;
        }

        public String getShow_pic1 () {
            return show_pic1;
        }

        public void setShow_pic1 (String show_pic1) {
            this.show_pic1 = show_pic1;
        }

        public String getShow_pic4 () {
            return show_pic4;
        }

        public void setShow_pic4 (String show_pic4) {
            this.show_pic4 = show_pic4;
        }
    }
}
