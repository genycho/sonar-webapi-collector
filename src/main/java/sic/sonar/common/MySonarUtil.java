package sic.sonar.common;

public class MySonarUtil {
	private static final String SONAR_URL1 = "http://sonarqube1.tmonc.net";
	private static final String SONAR_URL2 = "http://sonarqube2.tmonc.net";
	private static final String SONAR_URL3 = "http://sonarqube3.tmonc.net";
	
	//[sonarqube1]
//	service_tmon_public_api
	//	service_tmon_promotion_api
//	service_tmon_gateway_api
//	service_tmon_log_api
//	service_tmon_msg_api
//	service_tmon_bridge_api
//	service_tmon_common_api
	
	//[sonarqube2]
//	service_tmon_media_api
//	service_tmon_search_extra2_api
//	service_tmon_search_new_api
//	service_tmon_category_list_api
//	service_tmon_tab_api
//	service_tmon_home_api
//	service_tmon_recommend_api
//	service_tmon_deal_detail_api
//	service_tmon_plan_api
//	service_tmon_board_api 	
	
	//[sonarqube3]	
//	service_tmon_coupon_api
//	service_tmon_discount_api	
//	service_tmon_marketing_api	
//	service_tmon_marketing_persona_api	
//	service_tmon_push_alarm_api
//	service_tmon_push_api
//	service_tmon_dealinfo_api
//	service_tmon_dealbuilder_api
//	service_tmon_dealpack_api
//	service_tmon_dealmax_api
//	service_tmon_ntpin_api
//	service_tmon_deal_manage_api
//	service_tmon_deal_standard_api
//	module_tmon_settlement
//	service_tmon_settlement_api
//	service_tmon_order_api
//	service_tmon_tda_api
//	service_tmon_wmstool_api
//	service_tmon_wms_api
//	service_tmon_pay_api
//	intra_tmon_cs_api
//	service_tmon_cs_api
//	service_tmon_contract_api
//	service_tmon_membership_api
//	service_tmon_member_api
//	service_tmon_partner_api
//	service_tmon_delivery_api
//	service_tmon_interwork_external_api
//	service_tmon_interwork_internal_api
//	module_tmon_interwork_core
//	service_tmon_banner_api

	static final String[] sonarServer1 = new String[] {"service_tmon_public_api","service_tmon_promotion_api","service_tmon_gateway_api","service_tmon_log_api","service_tmon_log_api","service_tmon_msg_api","service_tmon_bridge_api","service_tmon_common_api"};
	static final String[] sonarServer2 = new String[] {"service_tmon_media_api","service_tmon_search_extra2_api","service_tmon_search_new_api","service_tmon_category_list_api","service_tmon_tab_api","service_tmon_home_api","service_tmon_recommend_api","service_tmon_deal_detail_api","service_tmon_plan_api","service_tmon_board_api"};
	static final String[] sonarServer3 = new String[] {"service_tmon_coupon_api","service_tmon_discount_api","service_tmon_marketing_api","service_tmon_marketing_persona_api","service_tmon_push_alarm_api","service_tmon_push_api","service_tmon_dealinfo_api","service_tmon_dealbuilder_api","service_tmon_dealpack_api","service_tmon_dealmax_api","service_tmon_ntpin_api","service_tmon_deal_manage_api","service_tmon_deal_standard_api","module_tmon_settlement","service_tmon_settlement_api","service_tmon_order_api","service_tmon_tda_api","service_tmon_wmstool_api","service_tmon_wms_api","service_tmon_pay_api","intra_tmon_cs_api","service_tmon_cs_api","service_tmon_contract_api","service_tmon_membership_api","service_tmon_member_api","service_tmon_partner_api","service_tmon_delivery_api","service_tmon_interwork_external_api","service_tmon_interwork_internal_api","module_tmon_interwork_core","service_tmon_banner_api"};
	
	private static boolean isSonar1(String projectKey) {
		for(String toCheck : sonarServer1) {
			if(toCheck.equalsIgnoreCase(projectKey)) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean isSonar2(String projectKey) {
		for(String toCheck : sonarServer2) {
			if(toCheck.equalsIgnoreCase(projectKey)) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean isSonar3(String projectKey) {
		for(String toCheck : sonarServer3) {
			if(toCheck.equalsIgnoreCase(projectKey)) {
				return true;
			}
		}
		return false;
	}
	
	public static String getSonarServerURL(String projectKey) throws SICServiceException {
		String sonarUrl = null;
		if(isSonar1(projectKey.trim())) {
			sonarUrl = SONAR_URL1;
		}else if(isSonar2(projectKey.trim())){
			sonarUrl = SONAR_URL2;
		}else if(isSonar3(projectKey.trim())){
			sonarUrl = SONAR_URL3;
		}else {
			throw new SICServiceException("There is no matching SonarQube server info for - " + projectKey);
		}
		return sonarUrl;
	}

}
