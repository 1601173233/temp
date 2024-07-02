package cn.com.toolWeb.enums;

import cn.com.toolWeb.model.base.KeyNameVo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * excel sheet 去重类型 枚举类
 * @author jack.huang
 * @date 2020/4/24 17:38
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ExcelSheetDuplicateRemovalTypeEnum implements IEnumDict {
	/** 不去重 */
	NOT_CHECK(1,"不去重"),
	/** 忽略完全重复的记录 */
	ALL_SUIT (2,"忽略完全重复的记录"),
	/** 选择的字段重复，就忽略 */
	RANGE (3,"选择的字段重复，就忽略"),
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
	public static ExcelSheetDuplicateRemovalTypeEnum getByCode(Integer code) {
		if (code == null) {
			return null;
		}

		for (ExcelSheetDuplicateRemovalTypeEnum statusEnum : ExcelSheetDuplicateRemovalTypeEnum.values()) {
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

		for (ExcelSheetDuplicateRemovalTypeEnum statusEnum : ExcelSheetDuplicateRemovalTypeEnum.values()) {
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

		for (ExcelSheetDuplicateRemovalTypeEnum statusEnum : ExcelSheetDuplicateRemovalTypeEnum.values()) {
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
		return "EXCEL_SHEET_DUPLICATE_REMOVAL_TYPE";
	}
}
