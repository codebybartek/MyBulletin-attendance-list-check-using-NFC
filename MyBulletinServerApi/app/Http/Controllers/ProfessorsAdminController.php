<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Professor;
use App\Http\Resources\Professor as ProfessorsResource;
use Illuminate\Foundation\Auth\User;
use Tymon\JWTAuth\Facades\JWTAuth;

class ProfessorsAdminController extends Controller
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
        if($isAdmin) {
            $professor = Professor::all();
            return $professor;
        }else{
            return "unauthorized";
        }
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
        if($isAdmin) {
            $request['password'] = bcrypt($request->password);
            Professor::create($request->all());

            return response()->json([
                'created' => 'Professor was added'
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
        $professor = Professor::all()->where('id', $id);
        return ProfessorsResource::collection($professor);
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
            $professor = Professor::all()->where('id', $id)->first();
            return $professor;
        }else {
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
            $professor = Professor::all()->where('id', $id)->first();
            $password = Professor::all()->where('id', $id)->pluck('password');
            $professor->name = $request->name;
            $professor->email = $request->email;
            $professor->tagId = $request->tagId;
            if ($request->password != "") {
                $professor->password = bcrypt($request->password);
            }
            $professor->save();

            return response()->json([
                'updated' => 'Professor was updated'
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
            $professor = Professor::where('id', $id);
            $professor->delete();

            return response()->json([
                'deleted' => 'Professor was deleted'
            ], 200);
        }else{
            return "unauthorized";
        }
    }
}
