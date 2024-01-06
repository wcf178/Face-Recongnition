import json
import face_recognition
import numpy as np

from flask import request, Flask

app = Flask(__name__)
app.config['JSON_AS_ASCII'] = False

# python转换为json对象的转换规则
class NpEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, np.integer):
            return int(obj)
        elif isinstance(obj, np.floating):
            return float(obj)
        elif isinstance(obj, np.ndarray):
            return obj.tolist()
        else:
            return super(NpEncoder, self).default(obj)

@app.route('/faceapi', methods=['POST'])
def post_Data():

    postdata = request.json
    print(postdata)

    #读取图片
    if(request.json['flag']=="true"):
        #获取图片存储路径
        data = request.json['url']
        #获取用户名
        
        # 进行解析

        #添加用户到列表中
        
        face_encodings=[]

        check_image = face_recognition.load_image_file(data)
        #print(check_image)
        face_encodings = face_recognition.face_encodings(check_image)
        
        #添加人脸编码添加到列表中
        #face_encodings.append(face_encoding)
        #进行用户和人脸编码映射
        #face_dic = dict(map(lambda x, y: [x, y], username_list, face_encodings))
        #print(face_encodings[0])
        #faceEncoding = encoding_FaceStr(face_encodings[0])
        # 把python对象转换为json对象
        if(len(face_encodings) == 0):
            errormessage = 'NO_FACE'
            json_face = json.dumps(errormessage, cls=NpEncoder)
            return json_face
        else:
            json_face = json.dumps(face_encodings[0], cls=NpEncoder)
            return json_face

@app.route('/faceCompare', methods=['POST'])
def post_Data1():

    postdata = request.json
    print(postdata)

    #读取图片
    if(request.json['flag']=="true"):
        #获取考勤录入的faceCode
        newfaceCode= request.json['newFaceCode']
        #获取数据库中的faceCode
        faceCode = request.json['faceCode']
        #先看一眼数据
        
        #要先转换为数组才能进行对比
        faceCodenp = decoding_FaceStr(faceCode)
        newfaceCodenp = decoding_FaceStr(newfaceCode)
        #转换之后还要添加一个维度......
        #np.expand_dims(faceCodenp,axis=0)
        #np.expand_dims(newfaceCodenp,axis=0)
        faceCodenp1 = faceCodenp[np.newaxis, :]
        newfaceCodenp1 = newfaceCodenp[np.newaxis, :]
        print(newfaceCodenp)
        print(faceCodenp)
        # 进行对比
        matche = face_recognition.compare_faces(faceCodenp1, newfaceCodenp1,0.6)
        #添加用户到列表中
        
        print(matche)

        result = 'YES' if matche[0] else "NO"

        # 把python对象转换为json对象
        matchFlag = json.dumps(result, cls=NpEncoder)
        return matchFlag
    
def encoding_FaceStr(image_face_encoding):
    # 将numpy array类型转化为列表
    encoding__array_list = image_face_encoding.tolist()
    # 将列表里的元素转化为字符串
    encoding_str_list = [str(i) for i in encoding__array_list]
     # 拼接列表里的字符串
    encoding_str = ','.join(encoding_str_list)
    return encoding_str
 
def decoding_FaceStr(encoding_str):
    # print("name=%s,encoding=%s" % (name, encoding))
    # 将字符串转为numpy ndarray类型，即矩阵
    # 转换成一个list
    dlist = encoding_str.strip(' ').split(',')
    # 将list中str转换为float
    dfloat = list(map(float, dlist))
    
    face_encoding = np.array(dfloat)
    
    return face_encoding


if __name__ == '__main__':
    app.run(debug=True, host='127.0.0.1', port=8888)