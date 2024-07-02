package cn.com.toolWeb.enums;

import cn.com.toolWeb.model.base.KeyNameVo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * excel 合并类型枚举类
 * @author jack.huang
 * @date 2020/4/24 17:38
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ExcelCombineTypeEnum implements IEnumDict {
	/** sheet 不合并 */
	NOT_COMBINE(1,"sheet 不合并"),
	/** 合并成一个sheet */
	ONE (2,"合并成一个sheet"),
	/** 同名sheet合并 */
	SAME_NAME (3,"同名sheet合并"),
	/** 同名sheet忽略 */
	IGNORE(4,"同名sheet忽略"),
	;

	/** 编码 */
	private final Integer code;

	/** 枚举值 */
	private final String value;

	/**
	 * 根据编码获取枚举值
	 *
	 * @param code 枚举编码
	 * @return 枚举对象
	 */
	public static ExcelCombineTypeEnum getByCode(Integer code) {
		if (code == null) {
			return null;
		}

		for (ExcelCombineTypeEnum statusEnum : ExcelCombineTypeEnum.values()) {
			if (statusEnum.code.equals(code)) {
				return statusEnum;
			}
		}
		return null;
	}

	/**
	 * 根据编码获取值
	 *
	 * @param code 编码
	 * @return 值
	 */
	public static String getValueByCode(Integer code) {
		if (code == null) {
			return null;
		}

		for (ExcelCombineTypeEnum statusEnum : ExcelCombineTypeEnum.values()) {
			if (statusEnum.code.equals(code)) {
				return statusEnum.value;
			}
		}
		return null;
	}

	/**
	 * 根据值获取编码
	 *
	 * @param value 值
	 * @return 编码
	 */
	public static Integer getCodeByValue(String value) {
		if (value == null) {
			return null;
		}

		for (ExcelCombineTypeEnum statusEnum : ExcelCombineTypeEnum.values()) {
			if (statusEnum.value.equals(value)) {
				return statusEnum.getCode();
			}
		}
		return null;
	}

	/**
	 * 判断制定的编码是否存在
	 *
	 * @param code 编码
	 * @return 是否存在
	 */
	public static Boolean isCodeExists(Integer code) {
		return getByCode(code) != null;
	}

	@Override
	public KeyNameVo getDict() {
		KeyNameVo keyNameVo = new KeyNameVo();
		keyNameVo.setKey(code.toString());
		keyNameVo.setValue(value);
		return keyNameVo;
	}

	@Override
	public String getDictType() {
		return "EXCEL_COMBINE_TYPE";
	}
}
