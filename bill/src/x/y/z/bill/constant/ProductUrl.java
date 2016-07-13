package x.y.z.bill.constant;

import io.alpha.web.constant.UrlConstant;

@UrlConstant(namespace = ProjectInfo.name)
public interface ProductUrl {

	String PRODUCT_LIST = "/list";
	
	String PRODUCT_LISTING = "/listing";
	
	String PRODUCT_DETAIL = "/detail/{productId}";
	
	String PRODUCT_BUY = "/invest";
}
