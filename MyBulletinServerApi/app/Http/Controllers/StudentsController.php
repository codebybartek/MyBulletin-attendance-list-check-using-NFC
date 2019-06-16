<?php

namespace App\Http\Controllers;

use App\Student;
use Illuminate\Http\Request;
use Illuminate\Foundation\Auth\User;
use Tymon\JWTAuth\Facades\JWTAuth;

class StudentsController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return Student::all();
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {

    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $professor = JWTAuth::parseToken()->authenticate();
        $isAdmin = $professor->isAdmin;
        if($isAdmin) {
            Student::create($request->all());

            return response()->json([
                'created' => 'Student was added'
            ], 201);
        }else{
            return "unauthorized";
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $professor = JWTAuth::parseToken()->authenticate();
        $isAdmin = $professor->isAdmin;
        if($isAdmin) {
            return Student::all()->where('student_id', $id)->first();
        }else{
            return "unauthorized";
        }
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        $professor = JWTAuth::parseToken()->authenticate();
        $isAdmin = $professor->isAdmin;
        if($isAdmin) {
            $student = Student::all()->where('student_id', $id);
            return $student;
        }else{
            return "unauthorized";
        }
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        $professor = JWTAuth::parseToken()->authenticate();
        $isAdmin = $professor->isAdmin;
        if($isAdmin) {
            $student = Student::all()->where('student_id', $id)->first();
            $student->name = $request->name;
            $student->email = $request->email;
            $student->tagId = $request->tagId;
            $student->save();

            return response()->json([
                'updated' => 'Student was updated'
            ], 201);
        }else{
            return "unauthorized";
        }
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $professor = JWTAuth::parseToken()->authenticate();
        $isAdmin = $professor->isAdmin;
        if($isAdmin) {
            $student = Student::where('student_id', $id);
            $student->delete();

            return response()->json([
                'deleted' => 'Student was deleted'
            ], 200);
        }else{
            return "unauthorized";        }
    }
}
