package com.qaid.hrms.service;

import com.qaid.hrms.model.dto.request.Demo3DRequest;
import com.qaid.hrms.model.dto.response.Demo3DResponse;
import java.util.List;

public interface Demo3DService {
    Demo3DResponse createDemo(Demo3DRequest request);
    Demo3DResponse updateDemoByAllocatedId(String allocatedId, Demo3DRequest request);
    void deleteDemoByAllocatedId(String allocatedId);
    Demo3DResponse getDemoByAllocatedId(String allocatedId);
    List<Demo3DResponse> getAllDemos();
}
