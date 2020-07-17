package net.cnki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import net.cnki.pojo.Menu;
import net.cnki.service.MenuService;

@RestController
public class Controller {

	@Autowired
	MenuService menuService;
	@RequestMapping("text")
	public PageInfo<Menu> text() {
		
		System.out.println(menuService.getMenuAll());
		System.out.println("----------");
		System.out.println(menuService.getMenuByContion());
		System.out.println("----------");
		System.out.println(menuService.getMenuByContions());
		System.out.println("----------");
		System.out.println(menuService.getMenuBysql(1));
		PageInfo<Menu> pageInfo =menuService.getMenuAll();
		return pageInfo;
		
	}
}
