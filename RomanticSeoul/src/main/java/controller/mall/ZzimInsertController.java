package controller.mall;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bean.Course;
import bean.Member;
import bean.Menu;
import bean.Myplan;
import bean.Store;
import controller.common.SuperClass;
import dao.MallDao;
import utility.MyplanList;

public class ZzimInsertController extends SuperClass {
	private final String command = "/zziminsert.ma" ;
	private ModelAndView mav = null ;
	private final String redirect = "redirect:/meLoginForm.me" ;
	
	@Autowired
	@Qualifier("malldao")
	private MallDao mdao ; 	
	
	public ZzimInsertController() {
		super("zzimList", "zzimList");
		this.mav = new ModelAndView();
	}
	
	@GetMapping(command)
	public ModelAndView doGet(
			@RequestParam(value="eatid", required = false) String eatid,
			@RequestParam(value="lookid", required = false) String lookid,
			@RequestParam(value="drinkid", required = false) String drinkid,
			@RequestParam(value="coseq", required = false) int coseq,
			@RequestParam(value="stock", required = false) int stock,
			HttpSession session) {
		System.out.println("zziminsert doget");
		Member loginfo = (Member)session.getAttribute("loginfo")  ;
		
		if (loginfo == null) { // 미로그인
			System.out.println("로그인이 필요합니다."); 
			String message = "로그인이 필요합니다." ;
			
			this.mav.addObject("errmsg", message) ;
			this.mav.setViewName(this.redirect); // go to login page			
			
		} else { // 로그인 함
			System.out.println("로그인함");
			MyplanList myplan = (MyplanList)session.getAttribute("myplan")  ;
			if(myplan == null) {
				myplan = new MyplanList();
			}
			if( !(eatid == null || eatid.isBlank())) { // eat가게 정보가 있으면
				myplan.AddOrder(eatid, stock,"eat");
			}else if( !(drinkid == null || drinkid.isBlank())) {
				myplan.AddOrder(drinkid, stock,"drink");
			}else if( !(lookid == null || drinkid.isBlank())) {
				myplan.AddOrder(lookid, stock,"look");
			}
			System.out.println("찜목록 추가 완료 /"+myplan.toString());
			session.setAttribute("myplan", myplan);
			mav.setViewName(super.getpage);
		}
		return this.mav ;
	}
	
	@PostMapping(command)
	public ModelAndView doPost(
			HttpSession session) {
		
		Member loginfo = (Member)session.getAttribute("loginfo")  ;
		Myplan myplan = (Myplan)session.getAttribute("myplan")  ;
		
		
		
		return this.mav ;
	}
}
