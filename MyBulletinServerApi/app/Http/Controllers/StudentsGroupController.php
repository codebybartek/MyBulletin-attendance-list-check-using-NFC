<?php

namespace App\Http\Controllers;

use App\StudentsGroup;
use App\Http\Resources\StudentsGroups as StudentsGroupsResource;
use Illuminate\Http\Request;
use Tymon\JWTAuth\Facades\JWTAuth;
use Illuminate\Foundation\Auth\User;

class StudentsGroupController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $professor = JWTAuth::parseToken()->authenticate();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            $studentsGroup = StudentsGroup::all();
            return StudentsGroupsResource::collection($studentsGroup);
        }
        return "unauthorized";
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
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
        if(!$isAdmin) {
            StudentsGroup::create($request->all());

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
        if(!$isAdmin) {
            $studentsGroup = StudentsGroup::all()->where('group_id', $id);
            return StudentsGroupsResource::collection($studentsGroup);
        }
        return "unauthorized";
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
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
        //
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
        if(!$isAdmin) {
            $student = StudentsGroup::where('student_id', $id)->first();
            $student->delete();

            return response()->json([
                'deleted' => 'Student was deleted'
            ], 200);
        }else{
            return "unauthorized";
        }
    }
}
