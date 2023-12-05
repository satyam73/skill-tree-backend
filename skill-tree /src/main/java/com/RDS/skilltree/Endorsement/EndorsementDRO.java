package com.RDS.skilltree.Endorsement;

import com.RDS.skilltree.EndorsementList.EndorsementType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EndorsementDRO {
    @NotNull(message = "user id cannot be null")
    private UUID userId;
    @NotNull(message = "skill id cannot be null")
    private UUID skillId;
    private String description;
    private UUID endorsementId;
    private UUID endorserId;
    private EndorsementType type;

}
