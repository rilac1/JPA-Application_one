package jpabook.jpashop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;

	@GetMapping("/items/new")
	public String createForm(Model model) {
		model.addAttribute("form", new BookForm());
		return "items/createItemForm";
	}

	@PostMapping("/items/new")
	public String create(BookForm form) {
		Book book = new Book();
		book.setName(form.getName());
		book.setPrice(form.getPrice());
		book.setStockQuantity(form.getStockQuantity());
		book.setAuthor(form.getAuthor());
		book.setIsbn(form.getIsbn());

		itemService.saveItem(book);
		return "redirect:/";
	}

	@GetMapping("/items")
	public String list(Model model) {
		List<Item> items = itemService.findItems();
		model.addAttribute("items", items);
		return "items/itemList";
	}

	@GetMapping("items/{itemId}/edit")
	public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
		Book item = (Book) itemService.findOne(itemId);

		BookForm form = new BookForm();
		form.setId(item.getId());
		form.setName((form.getName()));
		form.setPrice(form.getPrice());
		form.setStockQuantity(item.getStockQuantity());
		form.setAuthor(item.getAuthor());
		form.setIsbn(item.getIsbn());

		model.addAttribute("form", form);
		return "items/updateItemForm";
	}

	@PostMapping("items/{itemId}/edit")
	public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {

		/**
		 * 컨트롤러에서 어설프게 엔티티를 생성하지 마라. (준영속 엔티티)
		 */
		// Book book = new Book();
		// book.setId(form.getId());
		// book.setName(form.getName());
		// book.setPrice(form.getPrice());
		// book.setStockQuantity(form.getStockQuantity());
		// book.setAuthor(form.getAuthor());
		// book.setIsbn(form.getIsbn());
		// itemService.saveItem(book);

		/**
		 * 필요한 데이터만 service 계층으로 넘기자.
		 * 또는 필요한 데이터가 많다면 service 계층에 별도의 DTO를 만들자.
		 * public void updateItem(Long itemID, UpdateItemDto itemDto) {...}
		 * (ID가 Dto에 들어가도 됨.)
		 */

		itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());

		return "redirect:/items";
	}
}
