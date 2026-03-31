#  Nvidia AI Connector for Camunda 8

The **Nvidia AI Connector** is a custom outbound connector to integrate with NVIDIA AI APIs for chat/completion use cases.

It enables dynamic invocation of multiple AI models directly from BPMN workflows and supports access to a range of models, including free models or trial credits, for cost-effective workflow automation.

---

## Features

* Integrates with NVIDIA AI Chat Completion API
* Enables orchestration of AI-driven tasks across microservices and external systems  
* Seamlessly integrates AI capabilities into Camunda 8 workflows  
* Supports multiple AI models (Qwen, Mistral, DeepSeek, etc.)
* Dynamic request payload (model, prompt)
* Structured response with usage tokens

---

## Connector UI

<img width="970" height="528" alt="image" src="https://github.com/user-attachments/assets/fe892f20-e3ac-48c7-abd4-2d93ca3531d2" />


---

## Compatibility

* Works with Camunda SaaS
* Works with Self-Managed Camunda

---

## Setup Guide

### Setting Up Camunda SaaS

1. Navigate to **Camunda SaaS**
2. Create a cluster using the latest version
3. Open your cluster → go to **API** section
4. Click **Create New Client**
5. Select the **Zeebe** checkbox
6. Click **Create**
7. Copy configuration from **Spring Boot tab**
8. Paste into your `application.properties`

---

### Setting Up Self-Managed Environment

1. Setup Camunda Self-Managed using Docker:
   https://docs.camunda.io/docs/self-managed/setup/deploy/local/docker-compose/

2. Use the following endpoints:

```properties
camunda.client.zeebe.grpc-address=http://localhost:26500
camunda.client.zeebe.rest-address=http://localhost:8088
```

3. Uncomment these properties in your **test resources**

---

## Tools Required

* Download Desktop Modeler (if needed):
  https://camunda.com/download/modeler/

---

## Launching Your Connector

1. Start your connector runtime:

```
io.camunda.nvidia.ai.LocalConnectorRuntime
```

2. Open:

   * Web Modeler OR Desktop Modeler

3. Create a new project

4. Upload connector template:
   https://github.com/ak-chaudhari423/nvidia-ai-connector/blob/main/element-templates/nvidia-ai-connector.json

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
   * Receiver
   * Message

5. Deploy and run the process



---
