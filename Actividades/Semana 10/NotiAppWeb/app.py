from flask import Flask, request, jsonify
from flask_cors import CORS
import firebase_admin
from firebase_admin import credentials, messaging

app = Flask(__name__)
CORS(app)  # Habilita CORS

cred = credentials.Certificate("noti.json")
firebase_admin.initialize_app(cred)

@app.route("/envio", methods=["POST"])
def enviar_notificacion():
    data = request.json
    titulo = data.get("title")
    cuerpo = data.get("body")

    if not titulo or not cuerpo:
        return jsonify({"success": False, "message": "Campos requeridos vacíos"}), 400

    try:
        mensaje = messaging.Message(
            notification=messaging.Notification(
                title=titulo,
                body=cuerpo
            ),
            topic="general"
        )
        respuesta = messaging.send(mensaje)
        return jsonify({"success": True, "id": respuesta}), 200

    except Exception as e:
        return jsonify({"success": False, "message": str(e)}), 500

if __name__ == "__main__":
    app.run(debug=True)
