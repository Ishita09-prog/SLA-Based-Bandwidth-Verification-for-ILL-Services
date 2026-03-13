import requests
import random
import time
import json
import logging
from datetime import datetime

# Load configuration
with open("config.json") as f:
    config = json.load(f)

AGENT_ID = config["agentId"]
URL = config["backendUrl"]
THRESHOLD = config["threshold"]
INTERVAL = config["intervalSeconds"]

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s | %(levelname)s | %(message)s"
)

def generate_bandwidth():
    download_speed = round(random.uniform(70, 130), 2)
    upload_speed = round(random.uniform(20, 60), 2)

    status = "COMPLIANT" if download_speed >= THRESHOLD else "VIOLATION"

    return {
        "downloadSpeed": download_speed,
        "uploadSpeed": upload_speed,
        "status": status,
        "timestamp": datetime.now().isoformat()
    }

def send_data(payload):
    try:
        response = requests.post(URL, json=payload, timeout=5)

        if response.status_code == 200:
            logging.info(
                f"[{AGENT_ID}] Sent | "
                f"Download: {payload['downloadSpeed']} Mbps | "
                f"Status: {payload['status']}"
            )
        else:
            logging.warning(f"[{AGENT_ID}] Failed: {response.status_code}")

    except requests.exceptions.RequestException as e:
        logging.error(f"[{AGENT_ID}] Error: {e}")

def main():
    logging.info(f"SLA Monitoring Agent Started | Agent ID: {AGENT_ID}")

    while True:
        data = generate_bandwidth()
        send_data(data)
        time.sleep(INTERVAL)

if __name__ == "__main__":
    main()