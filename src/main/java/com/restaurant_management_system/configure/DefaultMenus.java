package com.restaurant_management_system.configure;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.restaurant_management_system.model.menuItems;
import com.restaurant_management_system.repository.menuRepo;

@Configuration
public class DefaultMenus {
	private boolean isIncluded = false;
	
	@Bean
	CommandLineRunner commandLineRunner(menuRepo menuRepo) {
		return args -> {
			if(!isIncluded) {
				List<menuItems> list = Arrays.asList(
						new menuItems(1 ,"Pizza" , 50.50 , "Most Exquite meal with reasonable prices"),
						new menuItems(2, "Potato Salad",40.50 , "Pretty soon to be run out"),
						new menuItems(3,"Humbuger" , 20.50 , "Frisk out your mind "),
						new menuItems(4,"Sunkit" , 10.50 , "Natural taste"));
				for( menuItems items : list ) {
					menuRepo.menuMap.put( items.getId(), items);
				}
				menuRepo.nextId = 
						menuRepo.menuMap.keySet()
						.stream()
						.max(Integer::compareTo)
						.orElse(0) + 1 ;
				isIncluded = true;
				return;
			}
		};
	}
}
