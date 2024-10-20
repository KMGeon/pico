from fastapi import FastAPI, HTTPException
from typing import Dict
from langchain.chat_models import ChatOpenAI
from langchain.prompts import (
    SystemMessagePromptTemplate,
    HumanMessagePromptTemplate,
    PromptTemplate
)
from langchain.chains import LLMChain
from langchain_core.prompts import ChatPromptTemplate
from langchain_core.output_parsers import StrOutputParser
from dotenv import load_dotenv
import os
from pydantic import BaseModel

app = FastAPI()

# 환경 변수 로드
load_dotenv("key.env")
openai_api_key = os.getenv("OPENAI_API_KEY")

# OpenAI 모델 설정
llm = ChatOpenAI(
    api_key=openai_api_key,
    model_name="gpt-3.5-turbo",
    temperature=0.7,
    max_tokens=256,
    frequency_penalty=0.5,
    presence_penalty=0.5
)

history = []
output_parser = StrOutputParser()  # 텍스트 파서

def create_system_message(scientist_name: str):
    return SystemMessagePromptTemplate.from_template(
        "너는 쉽게 과학을 알려주는 선생님 역할이야. "
        f"과학자 {scientist_name}이 되어 학생과 대화를 나눌 거야. "
        "말투는 옛스럽게, 답변의 난이도는 중학생이 이해할 수 있도록 해줘. "
        "과학적 개념 설명 시 1개의 예시를 필수로 들어줘."
    )

class ChatBot:
    def __init__(self, scientist_name: str):
        # history = []
        self.system_message = create_system_message(scientist_name)

    def chat(self, user_input: str) -> str:
        # 현재 히스토리와 새 입력을 사용하여 프롬프트 생성
        conversation_history = "\n".join(history) if history else "대화 없음"
        context = f"이전 대화:\n{conversation_history}\n\n새로운 질문: {user_input}\n\n답변:"
        
        # 인간 메시지 생성 및 프롬프트 설정
        human_message = HumanMessagePromptTemplate.from_template(context)
        first_prompt = ChatPromptTemplate.from_messages([self.system_message, human_message])
        chain = first_prompt | llm | output_parser
        
        # LLM을 사용해 응답 생성 및 기록 업데이트
        result = chain.invoke({"input": user_input})
        history.append(f"사용자: {user_input}")
        history.append(f"챗봇: {result}")
        
        return result

    def summarize(self) -> str:
        summary_prompt = PromptTemplate(
            input_variables=["history"],
            template="""
            아래는 사용자와 챗봇의 대화입니다:
            {history}
            위 대화를 5줄로 요약해 주세요.
            """
        )
        summary_chain = LLMChain(llm=llm, prompt=summary_prompt)
        conversation_text = "\n".join(history)
        return summary_chain.run(history=conversation_text)
    
    def keyword(self) -> str:
        keyword_prompt = PromptTemplate(
            input_variables=["history"],
            template="""
            아래는 사용자와 챗봇의 대화입니다:
            {history}
            위 대화에서 중요한 키워드 3개를 뽑아줘.
            """
        )
        summary_chain = LLMChain(llm=llm, prompt=keyword_prompt)
        conversation_text = "\n".join(history)
        return summary_chain.run(history=conversation_text)

class ScientistRequest(BaseModel):
    
    scientistName: str
    request: str
    

@app.post("/scientist")
def get_scientist(data: ScientistRequest):
    try:
        user_input = data.request
        scientist_name = data.scientistName
    
        chatbot = ChatBot(scientist_name)
        response = chatbot.chat(user_input)
        
        print(user_input)
        return {"response": response}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@app.post("/summery")
def get_scientist(data: ScientistRequest):
    try:
        scientist_name = data.scientistName
        
        chatbot = ChatBot(scientist_name)
        
        return {"response": chatbot.keyword(), "summery": chatbot.summarize()}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)