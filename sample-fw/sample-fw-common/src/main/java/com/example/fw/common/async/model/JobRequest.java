package com.example.fw.common.async.model;

import java.io.Serializable;
import java.util.Map;
import java.util.StringJoiner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * ジョブの要求情報を管理するクラス
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobRequest implements Serializable {
	private static final long serialVersionUID = -7463515743016612451L;

	// ジョブID
	private String jobId;
	// ジョブパラメータ
	private Map<String, String> parameters;
	// ジョブ実行ID（再実行の場合）
	private Long jobExecutionId;
	
	// 再実行かどうか（Taskletモデルはリラン、Chunkモデルはリスタート）
	private boolean restart;

	/**
	 * パラメータ文字列を返却する
	 * 
	 * @return パラメータ文字列
	 */
	public String toParameterString() {
		StringJoiner sj = new StringJoiner(",");
		for (String key : parameters.keySet()) {
			sj.add(String.format("%s=%s", key, parameters.get(key)));
		}
		return sj.toString();
	}

	/**
	 * 
	 * JobRequestが有効な値かを返却する
	 * 
	 */
	public boolean isValid() {
		if (restart) {
			//リスタートの場合はジョブ実行IDが指定されていること
			return jobExecutionId != null;
		}
		//初回実行はジョブが指定されていること
		return jobId != null;		
	}
}
