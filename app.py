from flask import Flask, render_template, request, redirect, url_for
import datetime
import json
import requests

app = Flask(__name__)

GENERADORES_FILE = "/home/george/Escritorio/PracticaNro3/backend/media/generadores.json"

def cargar_generadores():
    try:
        with open(GENERADORES_FILE, 'r') as f:
            return json.load(f)
    except FileNotFoundError:
        return []

def guardar_generadores(generadores):
    with open(GENERADORES_FILE, 'w') as f:
        json.dump(generadores, f, indent=4)


@app.route('/')
def index():
    return redirect(url_for('generadores_lista'))

@app.route('/generadores_lista')
def generadores_lista():
    generadores = cargar_generadores()
    return render_template('generadores_lista.html', generadores=generadores)

@app.route('/crear_generador', methods=['GET', 'POST'])
def crear_generador():
    if request.method == 'POST':
        generador = {
            'id': len(cargar_generadores()) + 1,
            'modelo': request.form['modelo'],
            'uso': request.form['uso'],
            'coste': float(request.form['coste']),
            'consumoPorHora': float(request.form['consumoPorHora']),
            'capacidadGeneracion': float(request.form['capacidadGeneracion'])
        }
        generadores = cargar_generadores()
        generadores.append(generador)
        guardar_generadores(generadores)

        return redirect(url_for('generadores_lista'))

    return render_template('formulario_generadores.html', title='Crear Generador', generador={})




@app.route('/modificar_generador/<int:id>', methods=['GET', 'POST'])
def modificar_generador(id):
    generadores = cargar_generadores()
    generador = next((p for p in generadores if p['id'] == id), None)

    if request.method == 'POST':
        if generador:
     
            generador['modelo'] = request.form['modelo']
            generador['uso'] = request.form['uso']
            generador['coste'] = float(request.form['coste'])
            generador['consumoPorHora'] = float(request.form['consumoPorHora'])
            generador['capacidadGeneracion'] = float(request.form['capacidadGeneracion'])
            guardar_generadores(generadores)

            return redirect(url_for('generadores_lista'))

    return render_template('formulario_generadores.html', title='Modificar Generador', generador=generador)

@app.route('/eliminar_generador/<int:id>', methods=['GET'])
def eliminar_generador(id):
    generadores = cargar_generadores()
    generador = next((p for p in generadores if p['id'] == id), None)
    generadores = [p for p in generadores if p['id'] != id]
    guardar_generadores(generadores)

    return redirect(url_for('generadores_lista'))



@app.route('/ordenar', methods=['POST'])
def ordenar():
    metodo = request.form['metodo']  
    criterio = request.form['criterio']  
    orden = request.form['orden'] 

   
    url = f"http://localhost:8080/generador/ordenar/{metodo}/{criterio}/{orden}"
    response = requests.get(url)

    if response.status_code == 200:
        generadores = response.json()  
        return render_template('generadores_lista.html', generadores=generadores)


if __name__ == '__main__':
    app.run(debug=True)
