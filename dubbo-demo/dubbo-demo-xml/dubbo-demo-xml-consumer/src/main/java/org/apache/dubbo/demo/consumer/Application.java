/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.demo.consumer;

import org.apache.dubbo.demo.DemoService;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.concurrent.CompletableFuture;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.Constants;

public class Application {
  /**
   * In order to make sure multicast registry works, need to specify
   * '-Djava.net.preferIPv4Stack=true' before launch the application
   */
  public static void main(String[] args) throws Exception {
    ClassPathXmlApplicationContext context =
        new ClassPathXmlApplicationContext("spring/dubbo-consumer.xml");

    context.start();

    //    ConfigCenterConfig configCenter = new ConfigCenterConfig();
    //    configCenter.setAddress("zookeeper://127.0.0.1:2181");

    DemoService demoService = context.getBean("demoService", DemoService.class);

    while (true) {
      RpcContext.getContext().setAttachment(CommonConstants.TAG_KEY, "tag2");

      CompletableFuture<String> hello = demoService.sayHelloAsync("world");
      System.out.println("result: " + hello.get());

      RpcContext.getContext().setAttachment(CommonConstants.TAG_KEY, "tag2");

      String hello2 = demoService.sayHello("world");
      System.out.println("result: " + hello2);

      RpcContext.getContext().setAttachment(CommonConstants.TAG_KEY, "tag2");

      String hello3 = demoService.sayHello2("world 2");
      System.out.println("result: " + hello3);

      Thread.sleep(1000);
    }
  }
}
