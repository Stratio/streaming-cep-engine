/**
 * Copyright (C) 2014 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stratio.streaming.functions.validator;

import com.stratio.streaming.commons.constants.ReplyCode;
import com.stratio.streaming.commons.messages.StratioStreamingMessage;
import com.stratio.streaming.exception.RequestValidationException;

    public class StreamNameNotNullValidation implements RequestValidation {

    private final static String STREAM_NAME_NOT_NULL = "Stream name cannot be empty or null.";

    @Override
    public void validate(StratioStreamingMessage request) throws RequestValidationException {
        if (request.getStreamName() == null || request.getStreamName().equals("")) {
            throw new RequestValidationException(ReplyCode.KO_STREAM_OPERATION_NOT_ALLOWED.getCode(),
                    STREAM_NAME_NOT_NULL);
        }
    }
}