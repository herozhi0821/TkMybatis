package net.cnki.mapper;

import java.util.List;

import net.cnki.pojo.Menu;
import net.cnki.tk.MyMapper;

public interface MenuMapper extends MyMapper<Menu> {
	public List<Menu> getMenuBysql(Integer id);
}
