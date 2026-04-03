#  Nvidia AI Connector for Camunda 8

The **Nvidia AI Connector** is a custom outbound connector to integrate with NVIDIA AI APIs for chat/completion use cases.

It enables dynamic invocation of multiple AI models directly from BPMN workflows and supports access to a range of models, including free models or trial credits, for cost-effective workflow automation.

---

## Features

* Enables orchestration of AI-driven tasks across microservices and external systems  
* Seamlessly integrates AI capabilities into Camunda 8 workflows  
* Supports multiple AI models (Qwen, Mistral, DeepSeek,Meta, etc.)
* Dynamic request payload (model, prompt)
* Structured response with usage tokens

---

## Connector UI

<img width="978" height="520" alt="image" src="https://github.com/user-attachments/assets/b20b7db5-b4bd-4da8-b0fd-bff0e4142347" />


---

## Compatibility

* Works with Camunda SaaS
* Works with Self-Managed Camunda

---

## Setup Guide


### Setting Up in Saas/Self managed Environment:
1.	Navigate to Camunda SaaS.
2.	Upload the connector template from https://github.com/ak-chaudhari423/nvidia-ai-connector/blob/main/nvidia-ai-template.json or download it from marketplace.

---

## Tools Required

* Download Desktop Modeler (if needed):
  https://camunda.com/download/modeler/

---


## Using in Desktop Modeler

1. Go to:

```
Camunda Modeler → resources → element-templates
```

2. Paste the downloaded **Nvidia AI Template JSON**

3. Restart Modeler

---

## How to Get NVIDIA API Key

1. Go to NVIDIA AI Platform : https://build.nvidia.com/models
2. Sign up / log in
3. Navigate to API access section
4. Generate your API key
5. Copy and use it in the connector

---

## Create BPMN Process

1. Create a new BPMN diagram

2. Add a **Service Task**

3. Select **Nvidia AI Connector**

4. Configure:

   * API Key
   * Model 
   * Prompt

5. Deploy and run the process



---
