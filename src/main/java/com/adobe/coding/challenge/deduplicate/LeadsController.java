package com.adobe.coding.challenge.deduplicate;

import java.util.List;

public interface LeadsController {
    List<Lead> deduplicate(List<Lead> leads, String property);
}
