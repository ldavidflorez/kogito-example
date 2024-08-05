from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from typing import List
import requests
from email_sender import EmailSender

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


class Candidate(BaseModel):
    name: str
    email: str
    salary: int
    skills: str
    jobPosition: str


class EmailRequest(BaseModel):
    toEmail: str
    subject: str
    body: str


candidates = []


@app.post("/register")
def register_candidate(candidate: Candidate):
    url = "http://localhost:8080/hiring"
    headers = {
        "accept": "*/*",
        "Content-Type": "application/json",
    }
    data = {"candidate": candidate.dict()}

    response = requests.post(url, headers=headers, json=data)
    print(response.text)
    if response.status_code != 200:
        raise HTTPException(status_code=response.status_code, detail=response.text)

    return response.json()


@app.get("/candidates", response_model=List[Candidate])
def list_candidates():
    return candidates


@app.post("/send-email")
def send_email(email_request: EmailRequest):
    email_sender = EmailSender()
    try:
        email_sender.send_email(
            to_email=email_request.toEmail,
            subject=email_request.subject,
            body=email_request.body,
        )
        return {"message": "Email sent successfully"}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
