<?php

namespace App\Http\Resources;

use App\Activity;
use App\AttendanceList;
use App\Group;
use Illuminate\Http\Resources\Json\JsonResource;
use App\Http\Resources\Groups as GroupsResource;
use Tymon\JWTAuth\Facades\JWTAuth;


class ActivitiesFree extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        $group = Group::all()->where('group_id', $this->group_id);

        $group = GroupsResource::collection($group);

        return [
            'id' => $this->activity_id,
            'activityDate' => $this->activityDate,
            'hour' => $this->hour,
            'title' => $this->title,
            'duration' => $this->duration,
            'recurrence_id' => $this->recurrence_id,
            'group' => $group,
            'checked' => $this->checked
        ];
    }
}
