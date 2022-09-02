package com.gw.stupid.consistency;

import com.gw.stupid.common.model.ReqResult;
import com.gw.stupid.common.model.ReqResultUtils;

import java.util.Map;

/**
 * @author guanwu
 * @created on 2022-08-08 16:48:19
 **/
public interface ConsistencyCommand {

    default ReqResult<String> executeCommands(Map<String, String> commands) {
        return ReqResultUtils.success();
    }

}
