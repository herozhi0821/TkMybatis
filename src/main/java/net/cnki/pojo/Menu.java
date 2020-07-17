package net.cnki.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "menu")
public class Menu {
    @Id
    private Integer id;

    private String url;

    private String menuName;

    private Integer parentId;

    private Date updateTime;

    private String remark;

    private String urlPre;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getUrlPre() {
        return urlPre;
    }

    public void setUrlPre(String urlPre) {
        this.urlPre = urlPre == null ? null : urlPre.trim();
    }

	@Override
	public String toString() {
		return "Menu [id=" + id + ", url=" + url + ", menuName=" + menuName + ", parentId=" + parentId + ", updateTime="
				+ updateTime + ", remark=" + remark + ", urlPre=" + urlPre + "]";
	}
    
}