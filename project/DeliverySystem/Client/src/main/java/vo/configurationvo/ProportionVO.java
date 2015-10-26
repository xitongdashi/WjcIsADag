package vo.configurationvo;

import java.util.Map;

import po.configurationdata.ProportionPO;
import po.configurationdata.enums.ConfigurationEnum;
import po.configurationdata.enums.DeliveryTypeEnum;

public class ProportionVO {
	private ConfigurationEnum ID=ConfigurationEnum.PRICE_PROPORTION;
	private Map<DeliveryTypeEnum,Integer> proportion;
	public int getByType(DeliveryTypeEnum type) {
		return proportion.get(type);
	}
	public ProportionVO(ProportionPO po){
		this.proportion=po.getProportion();
	}
}
