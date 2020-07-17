package net.cnki.service;

import net.cnki.pojo.Menu;

import java.util.List;

import com.github.pagehelper.PageInfo;

public interface MenuService {
    public PageInfo<Menu> getMenuAll();

    public List<Menu> getMenuByContions();

    public Menu getMenuByContion();
    
    public List<Menu> getMenuBysql(Integer id);

}
