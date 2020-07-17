package net.cnki.service.impl;

import net.cnki.mapper.MenuMapper;
import net.cnki.pojo.Menu;
import net.cnki.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuMapper menuMapper;

    @Override
    public PageInfo<Menu> getMenuAll() {
    	PageHelper.startPage(1, 10, "update_time desc");
    	PageInfo<Menu> pageInfo = new PageInfo<Menu>(menuMapper.selectAll());
        return pageInfo;
    }

    @Override
    public List<Menu> getMenuByContions() {
        Example example = new Example(Menu.class);
        example.createCriteria().andBetween("updateTime", "2020-06-18 17:06:40", "2020-06-18 17:10:40");
        example.setOrderByClause("id desc");
        
        return menuMapper.selectByExample(example);
    }

    @Override
    public Menu getMenuByContion() {
        Example example = new Example(Menu.class);
        example.createCriteria().andEqualTo("menuName","上传检测");
        return menuMapper.selectOneByExample(example);
    }

	@Override
	public List<Menu> getMenuBysql(Integer id) {
		// TODO Auto-generated method stub
		return menuMapper.getMenuBysql(id);
	}
}
