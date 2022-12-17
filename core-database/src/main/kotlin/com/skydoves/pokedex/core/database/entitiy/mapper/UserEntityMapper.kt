/*
 * Designed and developed by 2022 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skydoves.pokedex.core.database.entitiy.mapper

import com.skydoves.pokedex.core.database.entitiy.User
import com.skydoves.pokedex.core.model.ApiUser

object UserEntityMapper : EntityMapper<ApiUser, User> {

  override fun asEntity(domain: ApiUser): User {
    return User(
      id = domain.id,
      name = domain.name,
      email = domain.email,
      avatar = domain.avatar
    )
  }

  override fun asDomain(entity: User): ApiUser {
    return ApiUser(
      id = entity.id,
      name = entity.name ?: "",
      email = entity.email ?: "",
      avatar = entity.avatar ?: ""
    )
  }
}

fun ApiUser.asEntity(): User {
  return UserEntityMapper.asEntity(this)
}

fun User.asDomain(): ApiUser {
  return UserEntityMapper.asDomain(this)
}
