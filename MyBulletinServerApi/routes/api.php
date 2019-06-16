<?php

use Illuminate\Http\Request;


/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::post('login', 'ProfessorsController@login');
Route::post('loginMobileApp', 'ProfessorsController@loginMobileApp');
Route::group([
    'middleware' => ['jwt.auth', ]

], function () {
    Route::resource('courses', 'CoursesController');
    Route::resource('groups', 'GroupsController');
    Route::resource('activities', 'ActivitiesController');
    Route::resource('students', 'StudentsController');
    Route::resource('recurrences', 'RecurrenceController');
    Route::resource('attandancelist', 'AttandanceListController');
    Route::resource('activitiesfree', 'ActivitiesFreeController');
    Route::resource('professors', 'ProfessorsAdminController');
    Route::resource('subjects', 'SubjectsController');
    Route::resource('studentsgroup', 'StudentsGroupController');
});



