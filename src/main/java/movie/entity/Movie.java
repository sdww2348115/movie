package movie.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdww on 2015/11/23.
 */
public class Movie {

    /*
    movie的ID
     */
    private int id;

    /*
    电影标题
     */
    private String title;

    /*
    电影封面
     */
    private String image;

    /*
    导演
     */
    private List<String> director = new ArrayList<String>();

    /*
    主演
     */
    private List<String> actor = new ArrayList<String>();

    /*
    类型
     */
    private List<String> genre = new ArrayList<String>();

    /*
    上映时间
     */
    private String initialReleaseDate;

    /*
    播放时长
     */
    private Integer runtime;

    /*
    评分
     */
    private BigDecimal averageScore;

    /*
    总评分人数
     */
    private Integer ratingNum;

    /*
    简介
     */
    private String summary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getDirector() {
        return director;
    }

    public void setDirector(List<String> director) {
        this.director = director;
    }

    public List<String> getActor() {
        return actor;
    }

    public void setActor(List<String> actor) {
        this.actor = actor;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public String getInitialReleaseDate() {
        return initialReleaseDate;
    }

    public void setInitialReleaseDate(String initialReleaseDate) {
        this.initialReleaseDate = initialReleaseDate;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public BigDecimal getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(BigDecimal averageScore) {
        this.averageScore = averageScore;
    }

    public Integer getRatingNum() {
        return ratingNum;
    }

    public void setRatingNum(Integer ratingNum) {
        this.ratingNum = ratingNum;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
