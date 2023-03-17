package eu.passage.upperware.commons.service.store;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class SecureStoreDBServiceTest {

    @Mock private SecureStoreDBService secureStoreDBService;

    @Test
    void fillingSecureVariablesInTextTest() {
        String text = "script configuration ComponentMasterConfiguration{\n" +
                "download echo start downloading master >> ~/count_time.log && date +%T >> ~/count_time.log && rm -rf ~/melodic && mkdir ~/melodic && cd ~/melodic && wget https://s3.eu-west-1.amazonaws.com/melodic.testing.data/mdfs/genom_jm_VERY_BIG.sh && chmod +x ~/melodic/genom_jm_VERY_BIG.sh && ~/melodic/genom_jm_VERY_BIG.sh download_master && echo stop downloading master >> ~/count_time.log && date +%T >> ~/count_time.log\n" +
                "install  ~/melodic/genom_jm_VERY_BIG.sh install && export GENOMNEW_AWS_SECRET={{GENOMNEW_AWS_SECRET}} && echo GENOMNEW_AWS_SECRET={{GENOMNEW_AWS_SECRET}}\n" +
                "configure export GENOMNEW_AWS_SECRET={{GENOMNEW_AWS_SECRET}} && export GENOMNEW_AWS_KEY={{GENOMNEW_AWS_KEY}}\n" +
                "start printenv >> ~/melodic/env.txt &&~/melodic/genom_jm_VERY_BIG.sh start_master_submit\n" +
                "}\n";
        lenient().when(secureStoreDBService.getSecureVariable("GENOMNEW_AWS_SECRET")).thenReturn("password1");
        lenient().when(secureStoreDBService.getSecureVariable("GENOMNEW_AWS_KEY")).thenReturn("password2");
        lenient().when(secureStoreDBService.fillSecureVariablesInText(any())).thenCallRealMethod();
        String result = secureStoreDBService.fillSecureVariablesInText(text);
        assert result.equals("script configuration ComponentMasterConfiguration{\n" +
                "download echo start downloading master >> ~/count_time.log && date +%T >> ~/count_time.log && rm -rf ~/melodic && mkdir ~/melodic && cd ~/melodic && wget https://s3.eu-west-1.amazonaws.com/melodic.testing.data/mdfs/genom_jm_VERY_BIG.sh && chmod +x ~/melodic/genom_jm_VERY_BIG.sh && ~/melodic/genom_jm_VERY_BIG.sh download_master && echo stop downloading master >> ~/count_time.log && date +%T >> ~/count_time.log\n" +
                "install  ~/melodic/genom_jm_VERY_BIG.sh install && export GENOMNEW_AWS_SECRET=password1 && echo GENOMNEW_AWS_SECRET=password1\n" +
                "configure export GENOMNEW_AWS_SECRET=password1 && export GENOMNEW_AWS_KEY=password2\n" +
                "start printenv >> ~/melodic/env.txt &&~/melodic/genom_jm_VERY_BIG.sh start_master_submit\n" +
                "}\n"
        );
    }


}
